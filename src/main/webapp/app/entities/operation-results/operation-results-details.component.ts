import { Component, Vue, Inject } from 'vue-property-decorator';

import { IOperationResults } from '@/shared/model/operation-results.model';
import OperationResultsService from './operation-results.service';

@Component
export default class OperationResultsDetails extends Vue {
  @Inject('operationResultsService') private operationResultsService: () => OperationResultsService;
  public operationResults: IOperationResults = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.operationResultsId) {
        vm.retrieveOperationResults(to.params.operationResultsId);
      }
    });
  }

  public retrieveOperationResults(operationResultsId) {
    this.operationResultsService()
      .find(operationResultsId)
      .then(res => {
        this.operationResults = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}

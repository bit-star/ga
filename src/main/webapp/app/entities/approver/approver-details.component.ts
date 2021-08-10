import { Component, Vue, Inject } from 'vue-property-decorator';

import { IApprover } from '@/shared/model/approver.model';
import ApproverService from './approver.service';

@Component
export default class ApproverDetails extends Vue {
  @Inject('approverService') private approverService: () => ApproverService;
  public approver: IApprover = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.approverId) {
        vm.retrieveApprover(to.params.approverId);
      }
    });
  }

  public retrieveApprover(approverId) {
    this.approverService()
      .find(approverId)
      .then(res => {
        this.approver = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}

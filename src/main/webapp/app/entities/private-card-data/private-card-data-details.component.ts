import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPrivateCardData } from '@/shared/model/private-card-data.model';
import PrivateCardDataService from './private-card-data.service';

@Component
export default class PrivateCardDataDetails extends Vue {
  @Inject('privateCardDataService') private privateCardDataService: () => PrivateCardDataService;
  public privateCardData: IPrivateCardData = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.privateCardDataId) {
        vm.retrievePrivateCardData(to.params.privateCardDataId);
      }
    });
  }

  public retrievePrivateCardData(privateCardDataId) {
    this.privateCardDataService()
      .find(privateCardDataId)
      .then(res => {
        this.privateCardData = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}

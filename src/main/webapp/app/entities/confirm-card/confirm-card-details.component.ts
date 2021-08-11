import { Component, Vue, Inject } from 'vue-property-decorator';

import { IConfirmCard } from '@/shared/model/confirm-card.model';
import ConfirmCardService from './confirm-card.service';

@Component
export default class ConfirmCardDetails extends Vue {
  @Inject('confirmCardService') private confirmCardService: () => ConfirmCardService;
  public confirmCard: IConfirmCard = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.confirmCardId) {
        vm.retrieveConfirmCard(to.params.confirmCardId);
      }
    });
  }

  public retrieveConfirmCard(confirmCardId) {
    this.confirmCardService()
      .find(confirmCardId)
      .then(res => {
        this.confirmCard = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}

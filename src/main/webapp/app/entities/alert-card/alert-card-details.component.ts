import { Component, Vue, Inject } from 'vue-property-decorator';

import { IAlertCard } from '@/shared/model/alert-card.model';
import AlertCardService from './alert-card.service';

@Component
export default class AlertCardDetails extends Vue {
  @Inject('alertCardService') private alertCardService: () => AlertCardService;
  public alertCard: IAlertCard = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.alertCardId) {
        vm.retrieveAlertCard(to.params.alertCardId);
      }
    });
  }

  public retrieveAlertCard(alertCardId) {
    this.alertCardService()
      .find(alertCardId)
      .then(res => {
        this.alertCard = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}

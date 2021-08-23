import { Component, Vue, Inject } from 'vue-property-decorator';

import PublicCardDataService from '@/entities/public-card-data/public-card-data.service';
import { IPublicCardData } from '@/shared/model/public-card-data.model';

import { IAlertCard, AlertCard } from '@/shared/model/alert-card.model';
import AlertCardService from './alert-card.service';

const validations: any = {
  alertCard: {
    text: {},
    userId: {},
  },
};

@Component({
  validations,
})
export default class AlertCardUpdate extends Vue {
  @Inject('alertCardService') private alertCardService: () => AlertCardService;
  public alertCard: IAlertCard = new AlertCard();

  @Inject('publicCardDataService') private publicCardDataService: () => PublicCardDataService;

  public publicCardData: IPublicCardData[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.alertCardId) {
        vm.retrieveAlertCard(to.params.alertCardId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.alertCard.id) {
      this.alertCardService()
        .update(this.alertCard)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('gaApp.alertCard.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.alertCardService()
        .create(this.alertCard)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('gaApp.alertCard.created', { param: param.id });
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    }
  }

  public retrieveAlertCard(alertCardId): void {
    this.alertCardService()
      .find(alertCardId)
      .then(res => {
        this.alertCard = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.publicCardDataService()
      .retrieve()
      .then(res => {
        this.publicCardData = res.data;
      });
  }
}

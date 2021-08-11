import { Component, Vue, Inject } from 'vue-property-decorator';

import PublicCardDataService from '@/entities/public-card-data/public-card-data.service';
import { IPublicCardData } from '@/shared/model/public-card-data.model';

import { IConfirmCard, ConfirmCard } from '@/shared/model/confirm-card.model';
import ConfirmCardService from './confirm-card.service';

const validations: any = {
  confirmCard: {
    text: {},
    finish: {},
    userId: {},
  },
};

@Component({
  validations,
})
export default class ConfirmCardUpdate extends Vue {
  @Inject('confirmCardService') private confirmCardService: () => ConfirmCardService;
  public confirmCard: IConfirmCard = new ConfirmCard();

  @Inject('publicCardDataService') private publicCardDataService: () => PublicCardDataService;

  public publicCardData: IPublicCardData[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.confirmCardId) {
        vm.retrieveConfirmCard(to.params.confirmCardId);
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
    if (this.confirmCard.id) {
      this.confirmCardService()
        .update(this.confirmCard)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('gaApp.confirmCard.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.confirmCardService()
        .create(this.confirmCard)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('gaApp.confirmCard.created', { param: param.id });
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

  public retrieveConfirmCard(confirmCardId): void {
    this.confirmCardService()
      .find(confirmCardId)
      .then(res => {
        this.confirmCard = res;
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

import { Component, Vue, Inject } from 'vue-property-decorator';

import PublicCardDataService from '@/entities/public-card-data/public-card-data.service';
import { IPublicCardData } from '@/shared/model/public-card-data.model';

import DdUserService from '@/entities/dd-user/dd-user.service';
import { IDdUser } from '@/shared/model/dd-user.model';

import { IPrivateCardData, PrivateCardData } from '@/shared/model/private-card-data.model';
import PrivateCardDataService from './private-card-data.service';

const validations: any = {
  privateCardData: {
    name: {},
    feeValue: {},
    reason: {},
    itemType: {},
    typesOfFee: {},
    agree: {},
    finish: {},
    status: {},
    content: {},
  },
};

@Component({
  validations,
})
export default class PrivateCardDataUpdate extends Vue {
  @Inject('privateCardDataService') private privateCardDataService: () => PrivateCardDataService;
  public privateCardData: IPrivateCardData = new PrivateCardData();

  @Inject('publicCardDataService') private publicCardDataService: () => PublicCardDataService;

  public publicCardData: IPublicCardData[] = [];

  @Inject('ddUserService') private ddUserService: () => DdUserService;

  public ddUsers: IDdUser[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.privateCardDataId) {
        vm.retrievePrivateCardData(to.params.privateCardDataId);
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
    if (this.privateCardData.id) {
      this.privateCardDataService()
        .update(this.privateCardData)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('gaApp.privateCardData.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.privateCardDataService()
        .create(this.privateCardData)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('gaApp.privateCardData.created', { param: param.id });
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

  public retrievePrivateCardData(privateCardDataId): void {
    this.privateCardDataService()
      .find(privateCardDataId)
      .then(res => {
        this.privateCardData = res;
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
    this.ddUserService()
      .retrieve()
      .then(res => {
        this.ddUsers = res.data;
      });
  }
}

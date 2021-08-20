import { Component, Vue, Inject } from 'vue-property-decorator';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import PublicCardDataService from '@/entities/public-card-data/public-card-data.service';
import { IPublicCardData } from '@/shared/model/public-card-data.model';

import DdUserService from '@/entities/dd-user/dd-user.service';
import { IDdUser } from '@/shared/model/dd-user.model';

import { IPrivateCardData, PrivateCardData } from '@/shared/model/private-card-data.model';
import PrivateCardDataService from './private-card-data.service';

const validations: any = {
  privateCardData: {
    agree: {},
    finish: {},
    authority: {},
    createdByMe: {},
    updateTime: {},
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

  public convertDateTimeFromServer(date: Date): string {
    if (date && dayjs(date).isValid()) {
      return dayjs(date).format(DATE_TIME_LONG_FORMAT);
    }
    return null;
  }

  public updateInstantField(field, event) {
    if (event.target.value) {
      this.privateCardData[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.privateCardData[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.privateCardData[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.privateCardData[field] = null;
    }
  }

  public retrievePrivateCardData(privateCardDataId): void {
    this.privateCardDataService()
      .find(privateCardDataId)
      .then(res => {
        res.updateTime = new Date(res.updateTime);
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

import { Component, Vue, Inject } from 'vue-property-decorator';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import DdUserService from '@/entities/dd-user/dd-user.service';
import { IDdUser } from '@/shared/model/dd-user.model';

import PublicCardDataService from '@/entities/public-card-data/public-card-data.service';
import { IPublicCardData } from '@/shared/model/public-card-data.model';

import { IOperationResults, OperationResults } from '@/shared/model/operation-results.model';
import OperationResultsService from './operation-results.service';

const validations: any = {
  operationResults: {
    operationType: {},
    time: {},
    text: {},
    operationSource: {},
  },
};

@Component({
  validations,
})
export default class OperationResultsUpdate extends Vue {
  @Inject('operationResultsService') private operationResultsService: () => OperationResultsService;
  public operationResults: IOperationResults = new OperationResults();

  @Inject('ddUserService') private ddUserService: () => DdUserService;

  public ddUsers: IDdUser[] = [];

  @Inject('publicCardDataService') private publicCardDataService: () => PublicCardDataService;

  public publicCardData: IPublicCardData[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.operationResultsId) {
        vm.retrieveOperationResults(to.params.operationResultsId);
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
    if (this.operationResults.id) {
      this.operationResultsService()
        .update(this.operationResults)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('gaApp.operationResults.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.operationResultsService()
        .create(this.operationResults)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('gaApp.operationResults.created', { param: param.id });
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
      this.operationResults[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.operationResults[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.operationResults[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.operationResults[field] = null;
    }
  }

  public retrieveOperationResults(operationResultsId): void {
    this.operationResultsService()
      .find(operationResultsId)
      .then(res => {
        res.time = new Date(res.time);
        this.operationResults = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.ddUserService()
      .retrieve()
      .then(res => {
        this.ddUsers = res.data;
      });
    this.publicCardDataService()
      .retrieve()
      .then(res => {
        this.publicCardData = res.data;
      });
  }
}

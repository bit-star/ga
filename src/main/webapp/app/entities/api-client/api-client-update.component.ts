import { Component, Vue, Inject } from 'vue-property-decorator';

import LinkSystemService from '@/entities/link-system/link-system.service';
import { ILinkSystem } from '@/shared/model/link-system.model';

import { IApiClient, ApiClient } from '@/shared/model/api-client.model';
import ApiClientService from './api-client.service';

const validations: any = {
  apiClient: {
    name: {},
    apiKey: {},
    apiSecret: {},
    enable: {},
  },
};

@Component({
  validations,
})
export default class ApiClientUpdate extends Vue {
  @Inject('apiClientService') private apiClientService: () => ApiClientService;
  public apiClient: IApiClient = new ApiClient();

  @Inject('linkSystemService') private linkSystemService: () => LinkSystemService;

  public linkSystems: ILinkSystem[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.apiClientId) {
        vm.retrieveApiClient(to.params.apiClientId);
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
    if (this.apiClient.id) {
      this.apiClientService()
        .update(this.apiClient)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('gaApp.apiClient.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.apiClientService()
        .create(this.apiClient)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('gaApp.apiClient.created', { param: param.id });
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

  public retrieveApiClient(apiClientId): void {
    this.apiClientService()
      .find(apiClientId)
      .then(res => {
        this.apiClient = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.linkSystemService()
      .retrieve()
      .then(res => {
        this.linkSystems = res.data;
      });
  }
}

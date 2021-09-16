import { Component, Vue, Inject } from 'vue-property-decorator';

import { IApiClient } from '@/shared/model/api-client.model';
import ApiClientService from './api-client.service';

@Component
export default class ApiClientDetails extends Vue {
  @Inject('apiClientService') private apiClientService: () => ApiClientService;
  public apiClient: IApiClient = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.apiClientId) {
        vm.retrieveApiClient(to.params.apiClientId);
      }
    });
  }

  public retrieveApiClient(apiClientId) {
    this.apiClientService()
      .find(apiClientId)
      .then(res => {
        this.apiClient = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}

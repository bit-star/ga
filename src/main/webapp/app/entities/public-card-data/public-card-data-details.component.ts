import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPublicCardData } from '@/shared/model/public-card-data.model';
import PublicCardDataService from './public-card-data.service';

@Component
export default class PublicCardDataDetails extends Vue {
  @Inject('publicCardDataService') private publicCardDataService: () => PublicCardDataService;
  public publicCardData: IPublicCardData = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.publicCardDataId) {
        vm.retrievePublicCardData(to.params.publicCardDataId);
      }
    });
  }

  public retrievePublicCardData(publicCardDataId) {
    this.publicCardDataService()
      .find(publicCardDataId)
      .then(res => {
        this.publicCardData = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}

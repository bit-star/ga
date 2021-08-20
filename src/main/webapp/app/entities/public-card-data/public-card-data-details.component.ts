import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { IPublicCardData } from '@/shared/model/public-card-data.model';
import PublicCardDataService from './public-card-data.service';

@Component
export default class PublicCardDataDetails extends mixins(JhiDataUtils) {
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

import { Component, Vue, Inject } from 'vue-property-decorator';

import { ITopboxes } from '@/shared/model/topboxes.model';
import TopboxesService from './topboxes.service';

@Component
export default class TopboxesDetails extends Vue {
  @Inject('topboxesService') private topboxesService: () => TopboxesService;
  public topboxes: ITopboxes = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.topboxesId) {
        vm.retrieveTopboxes(to.params.topboxesId);
      }
    });
  }

  public retrieveTopboxes(topboxesId) {
    this.topboxesService()
      .find(topboxesId)
      .then(res => {
        this.topboxes = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}

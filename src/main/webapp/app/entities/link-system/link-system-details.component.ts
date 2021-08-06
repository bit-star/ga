import { Component, Vue, Inject } from 'vue-property-decorator';

import { ILinkSystem } from '@/shared/model/link-system.model';
import LinkSystemService from './link-system.service';

@Component
export default class LinkSystemDetails extends Vue {
  @Inject('linkSystemService') private linkSystemService: () => LinkSystemService;
  public linkSystem: ILinkSystem = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.linkSystemId) {
        vm.retrieveLinkSystem(to.params.linkSystemId);
      }
    });
  }

  public retrieveLinkSystem(linkSystemId) {
    this.linkSystemService()
      .find(linkSystemId)
      .then(res => {
        this.linkSystem = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}

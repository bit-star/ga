import { Component, Vue, Inject } from 'vue-property-decorator';

import { IGroupMembers } from '@/shared/model/group-members.model';
import GroupMembersService from './group-members.service';

@Component
export default class GroupMembersDetails extends Vue {
  @Inject('groupMembersService') private groupMembersService: () => GroupMembersService;
  public groupMembers: IGroupMembers = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.groupMembersId) {
        vm.retrieveGroupMembers(to.params.groupMembersId);
      }
    });
  }

  public retrieveGroupMembers(groupMembersId) {
    this.groupMembersService()
      .find(groupMembersId)
      .then(res => {
        this.groupMembers = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}

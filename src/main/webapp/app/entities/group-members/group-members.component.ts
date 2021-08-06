import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IGroupMembers } from '@/shared/model/group-members.model';

import GroupMembersService from './group-members.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class GroupMembers extends Vue {
  @Inject('groupMembersService') private groupMembersService: () => GroupMembersService;
  private removeId: number = null;

  public groupMembers: IGroupMembers[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllGroupMemberss();
  }

  public clear(): void {
    this.retrieveAllGroupMemberss();
  }

  public retrieveAllGroupMemberss(): void {
    this.isFetching = true;
    this.groupMembersService()
      .retrieve()
      .then(
        res => {
          this.groupMembers = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: IGroupMembers): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeGroupMembers(): void {
    this.groupMembersService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('gaApp.groupMembers.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllGroupMemberss();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}

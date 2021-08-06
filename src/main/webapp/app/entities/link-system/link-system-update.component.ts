import { Component, Vue, Inject } from 'vue-property-decorator';

import WorkflowTemplateService from '@/entities/workflow-template/workflow-template.service';
import { IWorkflowTemplate } from '@/shared/model/workflow-template.model';

import { ILinkSystem, LinkSystem } from '@/shared/model/link-system.model';
import LinkSystemService from './link-system.service';

const validations: any = {
  linkSystem: {
    name: {},
  },
};

@Component({
  validations,
})
export default class LinkSystemUpdate extends Vue {
  @Inject('linkSystemService') private linkSystemService: () => LinkSystemService;
  public linkSystem: ILinkSystem = new LinkSystem();

  @Inject('workflowTemplateService') private workflowTemplateService: () => WorkflowTemplateService;

  public workflowTemplates: IWorkflowTemplate[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.linkSystemId) {
        vm.retrieveLinkSystem(to.params.linkSystemId);
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
    if (this.linkSystem.id) {
      this.linkSystemService()
        .update(this.linkSystem)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('gaApp.linkSystem.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.linkSystemService()
        .create(this.linkSystem)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('gaApp.linkSystem.created', { param: param.id });
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

  public retrieveLinkSystem(linkSystemId): void {
    this.linkSystemService()
      .find(linkSystemId)
      .then(res => {
        this.linkSystem = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.workflowTemplateService()
      .retrieve()
      .then(res => {
        this.workflowTemplates = res.data;
      });
  }
}

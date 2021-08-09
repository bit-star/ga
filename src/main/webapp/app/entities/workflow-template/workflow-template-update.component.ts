import { Component, Vue, Inject } from 'vue-property-decorator';

import PublicCardDataService from '@/entities/public-card-data/public-card-data.service';
import { IPublicCardData } from '@/shared/model/public-card-data.model';

import FormFieldService from '@/entities/form-field/form-field.service';
import { IFormField } from '@/shared/model/form-field.model';

import LinkSystemService from '@/entities/link-system/link-system.service';
import { ILinkSystem } from '@/shared/model/link-system.model';

import WorkflowInstanceService from '@/entities/workflow-instance/workflow-instance.service';
import { IWorkflowInstance } from '@/shared/model/workflow-instance.model';

import { IWorkflowTemplate, WorkflowTemplate } from '@/shared/model/workflow-template.model';
import WorkflowTemplateService from './workflow-template.service';

const validations: any = {
  workflowTemplate: {
    formId: {},
    workflowId: {},
    workflowName: {},
    workflowTypeId: {},
    workflowTypeName: {},
    ddGroupTemplateId: {},
    ddCardTemplateId: {},
    ddCardCallBackKey: {},
    ddRobotCode: {},
    eMobileCreatePageUrl: {},
    chatidField: {},
    sourceField: {},
    commentsField: {},
  },
};

@Component({
  validations,
})
export default class WorkflowTemplateUpdate extends Vue {
  @Inject('workflowTemplateService') private workflowTemplateService: () => WorkflowTemplateService;
  public workflowTemplate: IWorkflowTemplate = new WorkflowTemplate();

  @Inject('publicCardDataService') private publicCardDataService: () => PublicCardDataService;

  public publicCardData: IPublicCardData[] = [];

  @Inject('formFieldService') private formFieldService: () => FormFieldService;

  public formFields: IFormField[] = [];

  @Inject('linkSystemService') private linkSystemService: () => LinkSystemService;

  public linkSystems: ILinkSystem[] = [];

  @Inject('workflowInstanceService') private workflowInstanceService: () => WorkflowInstanceService;

  public workflowInstances: IWorkflowInstance[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.workflowTemplateId) {
        vm.retrieveWorkflowTemplate(to.params.workflowTemplateId);
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
    if (this.workflowTemplate.id) {
      this.workflowTemplateService()
        .update(this.workflowTemplate)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('gaApp.workflowTemplate.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.workflowTemplateService()
        .create(this.workflowTemplate)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('gaApp.workflowTemplate.created', { param: param.id });
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

  public retrieveWorkflowTemplate(workflowTemplateId): void {
    this.workflowTemplateService()
      .find(workflowTemplateId)
      .then(res => {
        this.workflowTemplate = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.publicCardDataService()
      .retrieve()
      .then(res => {
        this.publicCardData = res.data;
      });
    this.formFieldService()
      .retrieve()
      .then(res => {
        this.formFields = res.data;
      });
    this.linkSystemService()
      .retrieve()
      .then(res => {
        this.linkSystems = res.data;
      });
    this.workflowInstanceService()
      .retrieve()
      .then(res => {
        this.workflowInstances = res.data;
      });
  }
}

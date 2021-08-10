import { Component, Vue, Inject } from 'vue-property-decorator';

import WorkflowTemplateService from '@/entities/workflow-template/workflow-template.service';
import { IWorkflowTemplate } from '@/shared/model/workflow-template.model';

import { IFormField, FormField } from '@/shared/model/form-field.model';
import FormFieldService from './form-field.service';

const validations: any = {
  formField: {
    fieldname: {},
    value: {},
    fielddbtype: {},
    lablename: {},
    show: {},
    orderNum: {},
  },
};

@Component({
  validations,
})
export default class FormFieldUpdate extends Vue {
  @Inject('formFieldService') private formFieldService: () => FormFieldService;
  public formField: IFormField = new FormField();

  @Inject('workflowTemplateService') private workflowTemplateService: () => WorkflowTemplateService;

  public workflowTemplates: IWorkflowTemplate[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.formFieldId) {
        vm.retrieveFormField(to.params.formFieldId);
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
    if (this.formField.id) {
      this.formFieldService()
        .update(this.formField)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('gaApp.formField.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.formFieldService()
        .create(this.formField)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('gaApp.formField.created', { param: param.id });
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

  public retrieveFormField(formFieldId): void {
    this.formFieldService()
      .find(formFieldId)
      .then(res => {
        this.formField = res;
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

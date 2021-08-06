import { Component, Vue, Inject } from 'vue-property-decorator';

import { IWorkflowTemplate } from '@/shared/model/workflow-template.model';
import WorkflowTemplateService from './workflow-template.service';

@Component
export default class WorkflowTemplateDetails extends Vue {
  @Inject('workflowTemplateService') private workflowTemplateService: () => WorkflowTemplateService;
  public workflowTemplate: IWorkflowTemplate = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.workflowTemplateId) {
        vm.retrieveWorkflowTemplate(to.params.workflowTemplateId);
      }
    });
  }

  public retrieveWorkflowTemplate(workflowTemplateId) {
    this.workflowTemplateService()
      .find(workflowTemplateId)
      .then(res => {
        this.workflowTemplate = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}

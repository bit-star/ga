import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { IWorkflowInstance } from '@/shared/model/workflow-instance.model';
import WorkflowInstanceService from './workflow-instance.service';

@Component
export default class WorkflowInstanceDetails extends mixins(JhiDataUtils) {
  @Inject('workflowInstanceService') private workflowInstanceService: () => WorkflowInstanceService;
  public workflowInstance: IWorkflowInstance = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.workflowInstanceId) {
        vm.retrieveWorkflowInstance(to.params.workflowInstanceId);
      }
    });
  }

  public retrieveWorkflowInstance(workflowInstanceId) {
    this.workflowInstanceService()
      .find(workflowInstanceId)
      .then(res => {
        this.workflowInstance = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}

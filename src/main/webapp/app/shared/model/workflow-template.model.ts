import { IFormField } from '@/shared/model/form-field.model';
import { ILinkSystem } from '@/shared/model/link-system.model';
import { IWorkflowInstance } from '@/shared/model/workflow-instance.model';

export interface IWorkflowTemplate {
  id?: number;
  formId?: string | null;
  workflowId?: string | null;
  workflowName?: string | null;
  workflowTypeId?: string | null;
  workflowTypeName?: string | null;
  ddGroupTemplateId?: string | null;
  ddCardTemplateId?: string | null;
  ddCardCallBackKey?: string | null;
  ddRobotCode?: string | null;
  eMobileCreatePageUrl?: string | null;
  chatidField?: string | null;
  sourceField?: string | null;
  commentsField?: string | null;
  formFields?: IFormField[] | null;
  linkSystem?: ILinkSystem | null;
  workflowInstances?: IWorkflowInstance[] | null;
}

export class WorkflowTemplate implements IWorkflowTemplate {
  constructor(
    public id?: number,
    public formId?: string | null,
    public workflowId?: string | null,
    public workflowName?: string | null,
    public workflowTypeId?: string | null,
    public workflowTypeName?: string | null,
    public ddGroupTemplateId?: string | null,
    public ddCardTemplateId?: string | null,
    public ddCardCallBackKey?: string | null,
    public ddRobotCode?: string | null,
    public eMobileCreatePageUrl?: string | null,
    public chatidField?: string | null,
    public sourceField?: string | null,
    public commentsField?: string | null,
    public formFields?: IFormField[] | null,
    public linkSystem?: ILinkSystem | null,
    public workflowInstances?: IWorkflowInstance[] | null
  ) {}
}

import { IPublicCardData } from '@/shared/model/public-card-data.model';
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
  eMobileCreatePageUrl?: string | null;
  chatidField?: string | null;
  cardidField?: string | null;
  commentsField?: string | null;
  publicCardData?: IPublicCardData[] | null;
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
    public eMobileCreatePageUrl?: string | null,
    public chatidField?: string | null,
    public cardidField?: string | null,
    public commentsField?: string | null,
    public publicCardData?: IPublicCardData[] | null,
    public formFields?: IFormField[] | null,
    public linkSystem?: ILinkSystem | null,
    public workflowInstances?: IWorkflowInstance[] | null
  ) {}
}

import { IApprover } from '@/shared/model/approver.model';
import { IWorkflowTemplate } from '@/shared/model/workflow-template.model';
import { IDdUser } from '@/shared/model/dd-user.model';
import { IPublicCardData } from '@/shared/model/public-card-data.model';

import { WorkflowInstanceStatus } from '@/shared/model/enumerations/workflow-instance-status.model';
export interface IWorkflowInstance {
  id?: number;
  form?: string | null;
  ddCardId?: string | null;
  title?: string | null;
  ddCardTemplateId?: string | null;
  ddCardCallBackKey?: string | null;
  status?: WorkflowInstanceStatus | null;
  approvers?: IApprover[] | null;
  workflowTemplate?: IWorkflowTemplate | null;
  creator?: IDdUser | null;
  publicCardData?: IPublicCardData[] | null;
}

export class WorkflowInstance implements IWorkflowInstance {
  constructor(
    public id?: number,
    public form?: string | null,
    public ddCardId?: string | null,
    public title?: string | null,
    public ddCardTemplateId?: string | null,
    public ddCardCallBackKey?: string | null,
    public status?: WorkflowInstanceStatus | null,
    public approvers?: IApprover[] | null,
    public workflowTemplate?: IWorkflowTemplate | null,
    public creator?: IDdUser | null,
    public publicCardData?: IPublicCardData[] | null
  ) {}
}

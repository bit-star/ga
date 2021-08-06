import { IDdUser } from '@/shared/model/dd-user.model';
import { IWorkflowTemplate } from '@/shared/model/workflow-template.model';
import { IPublicCardData } from '@/shared/model/public-card-data.model';

export interface IWorkflowInstance {
  id?: number;
  form?: string | null;
  ddCardId?: string | null;
  title?: string | null;
  ddUsers?: IDdUser[] | null;
  workflowTemplate?: IWorkflowTemplate | null;
  publicCardData?: IPublicCardData | null;
}

export class WorkflowInstance implements IWorkflowInstance {
  constructor(
    public id?: number,
    public form?: string | null,
    public ddCardId?: string | null,
    public title?: string | null,
    public ddUsers?: IDdUser[] | null,
    public workflowTemplate?: IWorkflowTemplate | null,
    public publicCardData?: IPublicCardData | null
  ) {}
}

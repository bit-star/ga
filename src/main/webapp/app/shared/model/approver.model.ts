import { IWorkflowInstance } from '@/shared/model/workflow-instance.model';
import { IDdUser } from '@/shared/model/dd-user.model';

import { ApproverRole } from '@/shared/model/enumerations/approver-role.model';
export interface IApprover {
  id?: number;
  approverRole?: ApproverRole | null;
  oaUserId?: number | null;
  email?: string | null;
  workflowInstance?: IWorkflowInstance | null;
  ddUser?: IDdUser | null;
}

export class Approver implements IApprover {
  constructor(
    public id?: number,
    public approverRole?: ApproverRole | null,
    public oaUserId?: number | null,
    public email?: string | null,
    public workflowInstance?: IWorkflowInstance | null,
    public ddUser?: IDdUser | null
  ) {}
}

import { IPrivateCardData } from '@/shared/model/private-card-data.model';
import { IOperationResults } from '@/shared/model/operation-results.model';
import { IWorkflowInstance } from '@/shared/model/workflow-instance.model';
import { IConversation } from '@/shared/model/conversation.model';
import { IWorkflowTemplate } from '@/shared/model/workflow-template.model';

export interface IPublicCardData {
  id?: number;
  name?: string | null;
  fee?: string | null;
  reason?: string | null;
  itemType?: string | null;
  typesOfFee?: string | null;
  agree?: boolean | null;
  requestid?: number | null;
  workflowid?: number | null;
  valid?: boolean | null;
  privateCardData?: IPrivateCardData[] | null;
  operationResults?: IOperationResults[] | null;
  workflowInstance?: IWorkflowInstance | null;
  conversation?: IConversation | null;
  workflowTemplate?: IWorkflowTemplate | null;
}

export class PublicCardData implements IPublicCardData {
  constructor(
    public id?: number,
    public name?: string | null,
    public fee?: string | null,
    public reason?: string | null,
    public itemType?: string | null,
    public typesOfFee?: string | null,
    public agree?: boolean | null,
    public requestid?: number | null,
    public workflowid?: number | null,
    public valid?: boolean | null,
    public privateCardData?: IPrivateCardData[] | null,
    public operationResults?: IOperationResults[] | null,
    public workflowInstance?: IWorkflowInstance | null,
    public conversation?: IConversation | null,
    public workflowTemplate?: IWorkflowTemplate | null
  ) {
    this.agree = this.agree ?? false;
    this.valid = this.valid ?? false;
  }
}

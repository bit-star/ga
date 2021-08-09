import { IPrivateCardData } from '@/shared/model/private-card-data.model';
import { IOperationResults } from '@/shared/model/operation-results.model';
import { IWorkflowInstance } from '@/shared/model/workflow-instance.model';
import { IConversation } from '@/shared/model/conversation.model';

export interface IPublicCardData {
  id?: number;
  requestid?: number | null;
  workflowid?: number | null;
  valid?: boolean | null;
  link?: string | null;
  name?: string | null;
  feeValue?: string | null;
  reason?: string | null;
  itemType?: string | null;
  typesOfFee?: string | null;
  agree?: boolean | null;
  finish?: string | null;
  status?: string | null;
  content?: string | null;
  privateCardData?: IPrivateCardData[] | null;
  operationResults?: IOperationResults[] | null;
  workflowInstance?: IWorkflowInstance | null;
  conversation?: IConversation | null;
}

export class PublicCardData implements IPublicCardData {
  constructor(
    public id?: number,
    public requestid?: number | null,
    public workflowid?: number | null,
    public valid?: boolean | null,
    public link?: string | null,
    public name?: string | null,
    public feeValue?: string | null,
    public reason?: string | null,
    public itemType?: string | null,
    public typesOfFee?: string | null,
    public agree?: boolean | null,
    public finish?: string | null,
    public status?: string | null,
    public content?: string | null,
    public privateCardData?: IPrivateCardData[] | null,
    public operationResults?: IOperationResults[] | null,
    public workflowInstance?: IWorkflowInstance | null,
    public conversation?: IConversation | null
  ) {
    this.valid = this.valid ?? false;
    this.agree = this.agree ?? false;
  }
}

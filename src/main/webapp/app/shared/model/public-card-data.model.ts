import { IPrivateCardData } from '@/shared/model/private-card-data.model';
import { IOperationResults } from '@/shared/model/operation-results.model';
import { IConfirmCard } from '@/shared/model/confirm-card.model';
import { IWorkflowInstance } from '@/shared/model/workflow-instance.model';
import { IConversation } from '@/shared/model/conversation.model';

import { PublicDataCardStatus } from '@/shared/model/enumerations/public-data-card-status.model';
import { WorkflowInstanceStatus } from '@/shared/model/enumerations/workflow-instance-status.model';
export interface IPublicCardData {
  id?: number;
  requestid?: number | null;
  workflowid?: number | null;
  valid?: boolean | null;
  finish?: string | null;
  status?: PublicDataCardStatus | null;
  variables?: string | null;
  createdTime?: Date | null;
  link?: string | null;
  updateLink?: string | null;
  agreeNum?: number | null;
  refuseNum?: number | null;
  oaStatus?: WorkflowInstanceStatus | null;
  privateCardData?: IPrivateCardData[] | null;
  operationResults?: IOperationResults[] | null;
  confirmCards?: IConfirmCard[] | null;
  workflowInstance?: IWorkflowInstance | null;
  conversation?: IConversation | null;
}

export class PublicCardData implements IPublicCardData {
  constructor(
    public id?: number,
    public requestid?: number | null,
    public workflowid?: number | null,
    public valid?: boolean | null,
    public finish?: string | null,
    public status?: PublicDataCardStatus | null,
    public variables?: string | null,
    public createdTime?: Date | null,
    public link?: string | null,
    public updateLink?: string | null,
    public agreeNum?: number | null,
    public refuseNum?: number | null,
    public oaStatus?: WorkflowInstanceStatus | null,
    public privateCardData?: IPrivateCardData[] | null,
    public operationResults?: IOperationResults[] | null,
    public confirmCards?: IConfirmCard[] | null,
    public workflowInstance?: IWorkflowInstance | null,
    public conversation?: IConversation | null
  ) {
    this.valid = this.valid ?? false;
  }
}

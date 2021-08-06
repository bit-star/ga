import { IGroupMembers } from '@/shared/model/group-members.model';
import { IPrivateCardData } from '@/shared/model/private-card-data.model';
import { IOperationResults } from '@/shared/model/operation-results.model';
import { IWorkflowInstance } from '@/shared/model/workflow-instance.model';

export interface IDdUser {
  id?: number;
  unionid?: string | null;
  remark?: string | null;
  userid?: string | null;
  isLeaderInDepts?: string | null;
  isBoss?: boolean | null;
  hiredDate?: number | null;
  isSenior?: boolean | null;
  tel?: string | null;
  department?: string | null;
  workPlace?: string | null;
  orderInDepts?: string | null;
  mobile?: string | null;
  errmsg?: string | null;
  active?: boolean | null;
  avatar?: string | null;
  isAdmin?: boolean | null;
  isHide?: boolean | null;
  jobnumber?: string | null;
  name?: string | null;
  extattr?: string | null;
  stateCode?: string | null;
  position?: string | null;
  roles?: string | null;
  groupMembers?: IGroupMembers[] | null;
  privateCardData?: IPrivateCardData[] | null;
  operationResults?: IOperationResults[] | null;
  workflowInstance?: IWorkflowInstance | null;
}

export class DdUser implements IDdUser {
  constructor(
    public id?: number,
    public unionid?: string | null,
    public remark?: string | null,
    public userid?: string | null,
    public isLeaderInDepts?: string | null,
    public isBoss?: boolean | null,
    public hiredDate?: number | null,
    public isSenior?: boolean | null,
    public tel?: string | null,
    public department?: string | null,
    public workPlace?: string | null,
    public orderInDepts?: string | null,
    public mobile?: string | null,
    public errmsg?: string | null,
    public active?: boolean | null,
    public avatar?: string | null,
    public isAdmin?: boolean | null,
    public isHide?: boolean | null,
    public jobnumber?: string | null,
    public name?: string | null,
    public extattr?: string | null,
    public stateCode?: string | null,
    public position?: string | null,
    public roles?: string | null,
    public groupMembers?: IGroupMembers[] | null,
    public privateCardData?: IPrivateCardData[] | null,
    public operationResults?: IOperationResults[] | null,
    public workflowInstance?: IWorkflowInstance | null
  ) {
    this.isBoss = this.isBoss ?? false;
    this.isSenior = this.isSenior ?? false;
    this.active = this.active ?? false;
    this.isAdmin = this.isAdmin ?? false;
    this.isHide = this.isHide ?? false;
  }
}

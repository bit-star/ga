import { IConversation } from '@/shared/model/conversation.model';
import { IDdUser } from '@/shared/model/dd-user.model';

import { GroupRole } from '@/shared/model/enumerations/group-role.model';
export interface IGroupMembers {
  id?: number;
  groupRole?: GroupRole | null;
  conversation?: IConversation | null;
  ddUser?: IDdUser | null;
}

export class GroupMembers implements IGroupMembers {
  constructor(
    public id?: number,
    public groupRole?: GroupRole | null,
    public conversation?: IConversation | null,
    public ddUser?: IDdUser | null
  ) {}
}

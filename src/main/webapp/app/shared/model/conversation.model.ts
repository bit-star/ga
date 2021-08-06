import { IPublicCardData } from '@/shared/model/public-card-data.model';
import { IGroupMembers } from '@/shared/model/group-members.model';

export interface IConversation {
  id?: string;
  name?: string | null;
  publicCardData?: IPublicCardData[] | null;
  groupMembers?: IGroupMembers[] | null;
}

export class Conversation implements IConversation {
  constructor(
    public id?: string,
    public name?: string | null,
    public publicCardData?: IPublicCardData[] | null,
    public groupMembers?: IGroupMembers[] | null
  ) {}
}

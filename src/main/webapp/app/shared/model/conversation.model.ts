import { IPublicCardData } from '@/shared/model/public-card-data.model';
import { IDdUser } from '@/shared/model/dd-user.model';

export interface IConversation {
  id?: string;
  name?: string | null;
  publicCardData?: IPublicCardData[] | null;
  ddUsers?: IDdUser[] | null;
}

export class Conversation implements IConversation {
  constructor(
    public id?: string,
    public name?: string | null,
    public publicCardData?: IPublicCardData[] | null,
    public ddUsers?: IDdUser[] | null
  ) {}
}

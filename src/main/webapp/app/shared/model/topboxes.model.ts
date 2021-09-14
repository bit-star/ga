import { IPublicCardData } from '@/shared/model/public-card-data.model';
import { IConversation } from '@/shared/model/conversation.model';

export interface ITopboxes {
  id?: number;
  text?: string | null;
  link?: string | null;
  cardId?: string | null;
  auxiliary?: boolean | null;
  open?: boolean | null;
  publicCardData?: IPublicCardData | null;
  conversation?: IConversation | null;
}

export class Topboxes implements ITopboxes {
  constructor(
    public id?: number,
    public text?: string | null,
    public link?: string | null,
    public cardId?: string | null,
    public auxiliary?: boolean | null,
    public open?: boolean | null,
    public publicCardData?: IPublicCardData | null,
    public conversation?: IConversation | null
  ) {
    this.auxiliary = this.auxiliary ?? false;
    this.open = this.open ?? false;
  }
}

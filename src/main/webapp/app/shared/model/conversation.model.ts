import { IPublicCardData } from '@/shared/model/public-card-data.model';
import { ITopboxes } from '@/shared/model/topboxes.model';
import { IDdUser } from '@/shared/model/dd-user.model';

export interface IConversation {
  id?: string;
  name?: string | null;
  title?: string | null;
  owner?: string | null;
  ownerUserId?: string | null;
  chatid?: string | null;
  openConversationId?: string | null;
  conversationTag?: number | null;
  useridlist?: string | null;
  uuid?: string | null;
  icon?: string | null;
  showHistoryType?: string | null;
  searchable?: string | null;
  validationType?: string | null;
  chatBannedType?: string | null;
  mentionAllAuthority?: string | null;
  managementType?: string | null;
  templateId?: string | null;
  officialGroup?: boolean | null;
  enableScenegroup?: boolean | null;
  groupUrl?: string | null;
  time?: Date | null;
  publicCardData?: IPublicCardData[] | null;
  topboxes?: ITopboxes[] | null;
  ddUsers?: IDdUser[] | null;
}

export class Conversation implements IConversation {
  constructor(
    public id?: string,
    public name?: string | null,
    public title?: string | null,
    public owner?: string | null,
    public ownerUserId?: string | null,
    public chatid?: string | null,
    public openConversationId?: string | null,
    public conversationTag?: number | null,
    public useridlist?: string | null,
    public uuid?: string | null,
    public icon?: string | null,
    public showHistoryType?: string | null,
    public searchable?: string | null,
    public validationType?: string | null,
    public chatBannedType?: string | null,
    public mentionAllAuthority?: string | null,
    public managementType?: string | null,
    public templateId?: string | null,
    public officialGroup?: boolean | null,
    public enableScenegroup?: boolean | null,
    public groupUrl?: string | null,
    public time?: Date | null,
    public publicCardData?: IPublicCardData[] | null,
    public topboxes?: ITopboxes[] | null,
    public ddUsers?: IDdUser[] | null
  ) {
    this.officialGroup = this.officialGroup ?? false;
    this.enableScenegroup = this.enableScenegroup ?? false;
  }
}

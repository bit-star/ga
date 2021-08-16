import { IPublicCardData } from '@/shared/model/public-card-data.model';
import { IDdUser } from '@/shared/model/dd-user.model';

export interface IPrivateCardData {
  id?: number;
  agree?: boolean | null;
  finish?: string | null;
  authority?: string | null;
  createdByMe?: string | null;
  publicCardData?: IPublicCardData | null;
  ddUser?: IDdUser | null;
}

export class PrivateCardData implements IPrivateCardData {
  constructor(
    public id?: number,
    public agree?: boolean | null,
    public finish?: string | null,
    public authority?: string | null,
    public createdByMe?: string | null,
    public publicCardData?: IPublicCardData | null,
    public ddUser?: IDdUser | null
  ) {
    this.agree = this.agree ?? false;
  }
}

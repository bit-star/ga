import { IPublicCardData } from '@/shared/model/public-card-data.model';
import { IDdUser } from '@/shared/model/dd-user.model';

export interface IPrivateCardData {
  id?: number;
  name?: string | null;
  fee?: string | null;
  reason?: string | null;
  itemType?: string | null;
  typesOfFee?: string | null;
  agree?: boolean | null;
  publicCardData?: IPublicCardData | null;
  ddUser?: IDdUser | null;
}

export class PrivateCardData implements IPrivateCardData {
  constructor(
    public id?: number,
    public name?: string | null,
    public fee?: string | null,
    public reason?: string | null,
    public itemType?: string | null,
    public typesOfFee?: string | null,
    public agree?: boolean | null,
    public publicCardData?: IPublicCardData | null,
    public ddUser?: IDdUser | null
  ) {
    this.agree = this.agree ?? false;
  }
}

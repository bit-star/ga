import { IDdUser } from '@/shared/model/dd-user.model';
import { IPublicCardData } from '@/shared/model/public-card-data.model';

import { OperationType } from '@/shared/model/enumerations/operation-type.model';
import { OperationSource } from '@/shared/model/enumerations/operation-source.model';
export interface IOperationResults {
  id?: number;
  operationType?: OperationType | null;
  time?: Date | null;
  text?: string | null;
  operationSource?: OperationSource | null;
  ddUser?: IDdUser | null;
  publicCardData?: IPublicCardData | null;
}

export class OperationResults implements IOperationResults {
  constructor(
    public id?: number,
    public operationType?: OperationType | null,
    public time?: Date | null,
    public text?: string | null,
    public operationSource?: OperationSource | null,
    public ddUser?: IDdUser | null,
    public publicCardData?: IPublicCardData | null
  ) {}
}

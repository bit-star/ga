/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from '@/shared/date/filters';
import PublicCardDataService from '@/entities/public-card-data/public-card-data.service';
import { PublicCardData } from '@/shared/model/public-card-data.model';
import { PublicDataCardStatus } from '@/shared/model/enumerations/public-data-card-status.model';
import { WorkflowInstanceStatus } from '@/shared/model/enumerations/workflow-instance-status.model';

const error = {
  response: {
    status: null,
    data: {
      type: null,
    },
  },
};

const axiosStub = {
  get: sinon.stub(axios, 'get'),
  post: sinon.stub(axios, 'post'),
  put: sinon.stub(axios, 'put'),
  patch: sinon.stub(axios, 'patch'),
  delete: sinon.stub(axios, 'delete'),
};

describe('Service Tests', () => {
  describe('PublicCardData Service', () => {
    let service: PublicCardDataService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new PublicCardDataService();
      currentDate = new Date();
      elemDefault = new PublicCardData(
        123,
        0,
        0,
        false,
        'AAAAAAA',
        PublicDataCardStatus.Effect,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        false,
        'AAAAAAA',
        0,
        0,
        currentDate,
        WorkflowInstanceStatus.Launch
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            time: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault
        );
        axiosStub.get.resolves({ data: returnedFromService });

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should not find an element', async () => {
        axiosStub.get.rejects(error);
        return service
          .find(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should create a PublicCardData', async () => {
        const returnedFromService = Object.assign(
          {
            id: 123,
            time: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            time: currentDate,
          },
          returnedFromService
        );

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a PublicCardData', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a PublicCardData', async () => {
        const returnedFromService = Object.assign(
          {
            requestid: 1,
            workflowid: 1,
            valid: true,
            finish: 'BBBBBB',
            status: 'BBBBBB',
            variables: 'BBBBBB',
            link: 'BBBBBB',
            updateLink: 'BBBBBB',
            name: 'BBBBBB',
            feeValue: 'BBBBBB',
            reason: 'BBBBBB',
            itemType: 'BBBBBB',
            typesOfFee: 'BBBBBB',
            agree: true,
            content: 'BBBBBB',
            agreeNum: 1,
            refuseNum: 1,
            time: dayjs(currentDate).format(DATE_TIME_FORMAT),
            oaStatus: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            time: currentDate,
          },
          returnedFromService
        );
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a PublicCardData', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a PublicCardData', async () => {
        const patchObject = Object.assign(
          {
            workflowid: 1,
            finish: 'BBBBBB',
            status: 'BBBBBB',
            name: 'BBBBBB',
            feeValue: 'BBBBBB',
            agreeNum: 1,
            refuseNum: 1,
          },
          new PublicCardData()
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            time: currentDate,
          },
          returnedFromService
        );
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a PublicCardData', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of PublicCardData', async () => {
        const returnedFromService = Object.assign(
          {
            requestid: 1,
            workflowid: 1,
            valid: true,
            finish: 'BBBBBB',
            status: 'BBBBBB',
            variables: 'BBBBBB',
            link: 'BBBBBB',
            updateLink: 'BBBBBB',
            name: 'BBBBBB',
            feeValue: 'BBBBBB',
            reason: 'BBBBBB',
            itemType: 'BBBBBB',
            typesOfFee: 'BBBBBB',
            agree: true,
            content: 'BBBBBB',
            agreeNum: 1,
            refuseNum: 1,
            time: dayjs(currentDate).format(DATE_TIME_FORMAT),
            oaStatus: 'BBBBBB',
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            time: currentDate,
          },
          returnedFromService
        );
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve().then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of PublicCardData', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a PublicCardData', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a PublicCardData', async () => {
        axiosStub.delete.rejects(error);

        return service
          .delete(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });
    });
  });
});

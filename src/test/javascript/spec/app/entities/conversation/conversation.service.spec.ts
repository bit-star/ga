/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from '@/shared/date/filters';
import ConversationService from '@/entities/conversation/conversation.service';
import { Conversation } from '@/shared/model/conversation.model';

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
  describe('Conversation Service', () => {
    let service: ConversationService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new ConversationService();
      currentDate = new Date();
      elemDefault = new Conversation(
        'ABC',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        false,
        false,
        'AAAAAAA',
        currentDate
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

        return service.find('ABC').then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should not find an element', async () => {
        axiosStub.get.rejects(error);
        return service
          .find('ABC')
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should create a Conversation', async () => {
        const returnedFromService = Object.assign(
          {
            id: 'ABC',
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

      it('should not create a Conversation', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a Conversation', async () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            title: 'BBBBBB',
            owner: 'BBBBBB',
            ownerUserId: 'BBBBBB',
            chatid: 'BBBBBB',
            openConversationId: 'BBBBBB',
            conversationTag: 1,
            useridlist: 'BBBBBB',
            uuid: 'BBBBBB',
            icon: 'BBBBBB',
            showHistoryType: 'BBBBBB',
            searchable: 'BBBBBB',
            validationType: 'BBBBBB',
            chatBannedType: 'BBBBBB',
            mentionAllAuthority: 'BBBBBB',
            managementType: 'BBBBBB',
            templateId: 'BBBBBB',
            officialGroup: true,
            enableScenegroup: true,
            groupUrl: 'BBBBBB',
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
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a Conversation', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a Conversation', async () => {
        const patchObject = Object.assign(
          {
            name: 'BBBBBB',
            title: 'BBBBBB',
            ownerUserId: 'BBBBBB',
            chatid: 'BBBBBB',
            openConversationId: 'BBBBBB',
            conversationTag: 1,
            useridlist: 'BBBBBB',
            searchable: 'BBBBBB',
            validationType: 'BBBBBB',
            managementType: 'BBBBBB',
            templateId: 'BBBBBB',
            officialGroup: true,
            groupUrl: 'BBBBBB',
            time: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          new Conversation()
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

      it('should not partial update a Conversation', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of Conversation', async () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            title: 'BBBBBB',
            owner: 'BBBBBB',
            ownerUserId: 'BBBBBB',
            chatid: 'BBBBBB',
            openConversationId: 'BBBBBB',
            conversationTag: 1,
            useridlist: 'BBBBBB',
            uuid: 'BBBBBB',
            icon: 'BBBBBB',
            showHistoryType: 'BBBBBB',
            searchable: 'BBBBBB',
            validationType: 'BBBBBB',
            chatBannedType: 'BBBBBB',
            mentionAllAuthority: 'BBBBBB',
            managementType: 'BBBBBB',
            templateId: 'BBBBBB',
            officialGroup: true,
            enableScenegroup: true,
            groupUrl: 'BBBBBB',
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
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve().then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of Conversation', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a Conversation', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete('ABC').then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a Conversation', async () => {
        axiosStub.delete.rejects(error);

        return service
          .delete('ABC')
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });
    });
  });
});

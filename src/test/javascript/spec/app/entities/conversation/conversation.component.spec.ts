/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ConversationComponent from '@/entities/conversation/conversation.vue';
import ConversationClass from '@/entities/conversation/conversation.component';
import ConversationService from '@/entities/conversation/conversation.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('Conversation Management Component', () => {
    let wrapper: Wrapper<ConversationClass>;
    let comp: ConversationClass;
    let conversationServiceStub: SinonStubbedInstance<ConversationService>;

    beforeEach(() => {
      conversationServiceStub = sinon.createStubInstance<ConversationService>(ConversationService);
      conversationServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ConversationClass>(ConversationComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          conversationService: () => conversationServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      conversationServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

      // WHEN
      comp.retrieveAllConversations();
      await comp.$nextTick();

      // THEN
      expect(conversationServiceStub.retrieve.called).toBeTruthy();
      expect(comp.conversations[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      conversationServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 'ABC' });
      comp.removeConversation();
      await comp.$nextTick();

      // THEN
      expect(conversationServiceStub.delete.called).toBeTruthy();
      expect(conversationServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});

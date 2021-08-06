/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import ConversationDetailComponent from '@/entities/conversation/conversation-details.vue';
import ConversationClass from '@/entities/conversation/conversation-details.component';
import ConversationService from '@/entities/conversation/conversation.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Conversation Management Detail Component', () => {
    let wrapper: Wrapper<ConversationClass>;
    let comp: ConversationClass;
    let conversationServiceStub: SinonStubbedInstance<ConversationService>;

    beforeEach(() => {
      conversationServiceStub = sinon.createStubInstance<ConversationService>(ConversationService);

      wrapper = shallowMount<ConversationClass>(ConversationDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { conversationService: () => conversationServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundConversation = { id: 'ABC' };
        conversationServiceStub.find.resolves(foundConversation);

        // WHEN
        comp.retrieveConversation('ABC');
        await comp.$nextTick();

        // THEN
        expect(comp.conversation).toBe(foundConversation);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundConversation = { id: 'ABC' };
        conversationServiceStub.find.resolves(foundConversation);

        // WHEN
        comp.beforeRouteEnter({ params: { conversationId: 'ABC' } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.conversation).toBe(foundConversation);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});

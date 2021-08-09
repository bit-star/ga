/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import PublicCardDataUpdateComponent from '@/entities/public-card-data/public-card-data-update.vue';
import PublicCardDataClass from '@/entities/public-card-data/public-card-data-update.component';
import PublicCardDataService from '@/entities/public-card-data/public-card-data.service';

import PrivateCardDataService from '@/entities/private-card-data/private-card-data.service';

import OperationResultsService from '@/entities/operation-results/operation-results.service';

import WorkflowInstanceService from '@/entities/workflow-instance/workflow-instance.service';

import ConversationService from '@/entities/conversation/conversation.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('PublicCardData Management Update Component', () => {
    let wrapper: Wrapper<PublicCardDataClass>;
    let comp: PublicCardDataClass;
    let publicCardDataServiceStub: SinonStubbedInstance<PublicCardDataService>;

    beforeEach(() => {
      publicCardDataServiceStub = sinon.createStubInstance<PublicCardDataService>(PublicCardDataService);

      wrapper = shallowMount<PublicCardDataClass>(PublicCardDataUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          publicCardDataService: () => publicCardDataServiceStub,

          privateCardDataService: () => new PrivateCardDataService(),

          operationResultsService: () => new OperationResultsService(),

          workflowInstanceService: () => new WorkflowInstanceService(),

          conversationService: () => new ConversationService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.publicCardData = entity;
        publicCardDataServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(publicCardDataServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.publicCardData = entity;
        publicCardDataServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(publicCardDataServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPublicCardData = { id: 123 };
        publicCardDataServiceStub.find.resolves(foundPublicCardData);
        publicCardDataServiceStub.retrieve.resolves([foundPublicCardData]);

        // WHEN
        comp.beforeRouteEnter({ params: { publicCardDataId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.publicCardData).toBe(foundPublicCardData);
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

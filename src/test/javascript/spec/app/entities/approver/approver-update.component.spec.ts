/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import ApproverUpdateComponent from '@/entities/approver/approver-update.vue';
import ApproverClass from '@/entities/approver/approver-update.component';
import ApproverService from '@/entities/approver/approver.service';

import WorkflowInstanceService from '@/entities/workflow-instance/workflow-instance.service';

import DdUserService from '@/entities/dd-user/dd-user.service';

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
  describe('Approver Management Update Component', () => {
    let wrapper: Wrapper<ApproverClass>;
    let comp: ApproverClass;
    let approverServiceStub: SinonStubbedInstance<ApproverService>;

    beforeEach(() => {
      approverServiceStub = sinon.createStubInstance<ApproverService>(ApproverService);

      wrapper = shallowMount<ApproverClass>(ApproverUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          approverService: () => approverServiceStub,

          workflowInstanceService: () => new WorkflowInstanceService(),

          ddUserService: () => new DdUserService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.approver = entity;
        approverServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(approverServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.approver = entity;
        approverServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(approverServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundApprover = { id: 123 };
        approverServiceStub.find.resolves(foundApprover);
        approverServiceStub.retrieve.resolves([foundApprover]);

        // WHEN
        comp.beforeRouteEnter({ params: { approverId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.approver).toBe(foundApprover);
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

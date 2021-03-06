/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import ApproverDetailComponent from '@/entities/approver/approver-details.vue';
import ApproverClass from '@/entities/approver/approver-details.component';
import ApproverService from '@/entities/approver/approver.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Approver Management Detail Component', () => {
    let wrapper: Wrapper<ApproverClass>;
    let comp: ApproverClass;
    let approverServiceStub: SinonStubbedInstance<ApproverService>;

    beforeEach(() => {
      approverServiceStub = sinon.createStubInstance<ApproverService>(ApproverService);

      wrapper = shallowMount<ApproverClass>(ApproverDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { approverService: () => approverServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundApprover = { id: 123 };
        approverServiceStub.find.resolves(foundApprover);

        // WHEN
        comp.retrieveApprover(123);
        await comp.$nextTick();

        // THEN
        expect(comp.approver).toBe(foundApprover);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundApprover = { id: 123 };
        approverServiceStub.find.resolves(foundApprover);

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

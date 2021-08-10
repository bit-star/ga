/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ApproverComponent from '@/entities/approver/approver.vue';
import ApproverClass from '@/entities/approver/approver.component';
import ApproverService from '@/entities/approver/approver.service';

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
  describe('Approver Management Component', () => {
    let wrapper: Wrapper<ApproverClass>;
    let comp: ApproverClass;
    let approverServiceStub: SinonStubbedInstance<ApproverService>;

    beforeEach(() => {
      approverServiceStub = sinon.createStubInstance<ApproverService>(ApproverService);
      approverServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ApproverClass>(ApproverComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          approverService: () => approverServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      approverServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllApprovers();
      await comp.$nextTick();

      // THEN
      expect(approverServiceStub.retrieve.called).toBeTruthy();
      expect(comp.approvers[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      approverServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeApprover();
      await comp.$nextTick();

      // THEN
      expect(approverServiceStub.delete.called).toBeTruthy();
      expect(approverServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});

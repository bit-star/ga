/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ConfirmCardComponent from '@/entities/confirm-card/confirm-card.vue';
import ConfirmCardClass from '@/entities/confirm-card/confirm-card.component';
import ConfirmCardService from '@/entities/confirm-card/confirm-card.service';

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
  describe('ConfirmCard Management Component', () => {
    let wrapper: Wrapper<ConfirmCardClass>;
    let comp: ConfirmCardClass;
    let confirmCardServiceStub: SinonStubbedInstance<ConfirmCardService>;

    beforeEach(() => {
      confirmCardServiceStub = sinon.createStubInstance<ConfirmCardService>(ConfirmCardService);
      confirmCardServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ConfirmCardClass>(ConfirmCardComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          confirmCardService: () => confirmCardServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      confirmCardServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllConfirmCards();
      await comp.$nextTick();

      // THEN
      expect(confirmCardServiceStub.retrieve.called).toBeTruthy();
      expect(comp.confirmCards[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      confirmCardServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeConfirmCard();
      await comp.$nextTick();

      // THEN
      expect(confirmCardServiceStub.delete.called).toBeTruthy();
      expect(confirmCardServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});

/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import DdUserComponent from '@/entities/dd-user/dd-user.vue';
import DdUserClass from '@/entities/dd-user/dd-user.component';
import DdUserService from '@/entities/dd-user/dd-user.service';

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
  describe('DdUser Management Component', () => {
    let wrapper: Wrapper<DdUserClass>;
    let comp: DdUserClass;
    let ddUserServiceStub: SinonStubbedInstance<DdUserService>;

    beforeEach(() => {
      ddUserServiceStub = sinon.createStubInstance<DdUserService>(DdUserService);
      ddUserServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<DdUserClass>(DdUserComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          ddUserService: () => ddUserServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      ddUserServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllDdUsers();
      await comp.$nextTick();

      // THEN
      expect(ddUserServiceStub.retrieve.called).toBeTruthy();
      expect(comp.ddUsers[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      ddUserServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeDdUser();
      await comp.$nextTick();

      // THEN
      expect(ddUserServiceStub.delete.called).toBeTruthy();
      expect(ddUserServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});

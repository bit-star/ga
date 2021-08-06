/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import GroupMembersComponent from '@/entities/group-members/group-members.vue';
import GroupMembersClass from '@/entities/group-members/group-members.component';
import GroupMembersService from '@/entities/group-members/group-members.service';

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
  describe('GroupMembers Management Component', () => {
    let wrapper: Wrapper<GroupMembersClass>;
    let comp: GroupMembersClass;
    let groupMembersServiceStub: SinonStubbedInstance<GroupMembersService>;

    beforeEach(() => {
      groupMembersServiceStub = sinon.createStubInstance<GroupMembersService>(GroupMembersService);
      groupMembersServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<GroupMembersClass>(GroupMembersComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          groupMembersService: () => groupMembersServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      groupMembersServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllGroupMemberss();
      await comp.$nextTick();

      // THEN
      expect(groupMembersServiceStub.retrieve.called).toBeTruthy();
      expect(comp.groupMembers[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      groupMembersServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeGroupMembers();
      await comp.$nextTick();

      // THEN
      expect(groupMembersServiceStub.delete.called).toBeTruthy();
      expect(groupMembersServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});

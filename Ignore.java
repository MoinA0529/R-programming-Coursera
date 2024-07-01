import axios from 'axios';
import MockAdapter from 'axios-mock-adapter';
import { reshareService } from './reshareService';
import { BASE_URL_FOR_SAVE } from "../components/utils/constants";

describe('reshareService', () => {
  let mock;

  beforeEach(() => {
    mock = new MockAdapter(axios);
  });

  afterEach(() => {
    mock.reset();
  });

  it('should make POST requests for each selected docSpaceId', async () => {
    const documentObject = {
      icmpDocId: '123',
      icmpRepoId: '456',
      category: 'Test',
      filename: 'test.pdf',
      filesize: '1024',
      createdBy: 'user1',
      attestation: true
    };

    const repCodeOwnersSelected = {
      data: [
        { docSpaceId: 'space1', selected: true },
        { docSpaceId: 'space2', selected: false },
        { docSpaceId: 'space3', selected: true }
      ]
    };

    mock.onPost(`${BASE_URL_FOR_SAVE}/document/save`).reply(200);

    await reshareService(documentObject, repCodeOwnersSelected);

    expect(mock.history.post.length).toBe(2);

    expect(mock.history.post[0].url).toBe(`${BASE_URL_FOR_SAVE}/document/save`);
    expect(JSON.parse(mock.history.post[0].data)).toEqual({
      ...documentObject,
      docSpaceId: 'space1'
    });

    expect(mock.history.post[1].url).toBe(`${BASE_URL_FOR_SAVE}/document/save`);
    expect(JSON.parse(mock.history.post[1].data)).toEqual({
      ...documentObject,
      docSpaceId: 'space3'
    });

    mock.history.post.forEach(request => {
      expect(request.headers['Content-Type']).toBe('application/json');
    });
  });

  it('should not make any requests if no docSpaceIds are selected', async () => {
    const documentObject = { /* ... */ };
    const repCodeOwnersSelected = {
      data: [
        { docSpaceId: 'space1', selected: false },
        { docSpaceId: 'space2', selected: false }
      ]
    };

    await reshareService(documentObject, repCodeOwnersSelected);

    expect(mock.history.post.length).toBe(0);
  });

  it('should throw an error if the API request fails', async () => {
    const documentObject = { /* ... */ };
    const repCodeOwnersSelected = {
      data: [{ docSpaceId: 'space1', selected: true }]
    };

    mock.onPost(`${BASE_URL_FOR_SAVE}/document/save`).reply(500);

    await expect(reshareService(documentObject, repCodeOwnersSelected)).rejects.toThrow();
  });
});

.
import axios from 'axios';
import { reshareService } from '../save-reshare-service';
import { SAVE_FILE_URL } from "../../components/utils/constants";

jest.mock('axios');

describe('reshareService', () => {
  const mockDocumentObject = {
    icmpDocId: '123',
    icmpRepoId: '456',
    category: 'Test',
    filename: 'test.pdf',
    filesize: '1024',
    createdBy: 'user1',
    attestation: true,
    sharedWith: [
      { docId: 'space1', otherInfo: 'data1' },
      { docId: 'space2', otherInfo: 'data2' }
    ]
  };

  const mockRepCodeOwnersSelected = [
    { docSpaceId: 'space1', selected: true },
    { docSpaceId: 'space2', selected: true },
    { docSpaceId: 'space3', selected: true },
    { docSpaceId: 'space4', selected: false }
  ];

  beforeEach(() => {
    axios.post.mockClear();
  });

  it('should make POST requests only for non-shared docSpaceIds', async () => {
    axios.post.mockResolvedValue({ data: 'Success' });

    const result = await reshareService(mockDocumentObject, mockRepCodeOwnersSelected);

    expect(axios.post).toHaveBeenCalledTimes(1);
    expect(axios.post).toHaveBeenCalledWith(
      SAVE_FILE_URL,
      { ...mockDocumentObject, docSpaceId: 'space3' },
      { headers: { 'Content-Type': 'application/json' } }
    );
    
    expect(result).toEqual([
      { docSpaceId: 'space1', status: 'already_shared' },
      { docSpaceId: 'space2', status: 'already_shared' },
      { docSpaceId: 'space3', data: 'Success', status: 'success' }
    ]);
  });

  it('should not make any requests if all selected docSpaceIds are already shared', async () => {
    const allSharedRepCodeOwners = [
      { docSpaceId: 'space1', selected: true },
      { docSpaceId: 'space2', selected: true }
    ];

    const result = await reshareService(mockDocumentObject, allSharedRepCodeOwners);

    expect(axios.post).not.toHaveBeenCalled();
    expect(result).toEqual([
      { docSpaceId: 'space1', status: 'already_shared' },
      { docSpaceId: 'space2', status: 'already_shared' }
    ]);
  });

  it('should handle API errors correctly', async () => {
    const mockError = new Error('Network Error');
    axios.post.mockRejectedValue(mockError);

    const result = await reshareService(mockDocumentObject, [{ docSpaceId: 'space3', selected: true }]);

    expect(axios.post).toHaveBeenCalledTimes(1);
    expect(result).toEqual([
      { docSpaceId: 'space3', error: mockError, status: 'error' }
    ]);
  });

  it('should handle a mix of new shares, already shared, and errors', async () => {
    axios.post
      .mockResolvedValueOnce({ data: 'Success for space3' })
      .mockRejectedValueOnce(new Error('Error for space4'));

    const mixedRepCodeOwners = [
      { docSpaceId: 'space1', selected: true },
      { docSpaceId: 'space3', selected: true },
      { docSpaceId: 'space4', selected: true }
    ];

    const result = await reshareService(mockDocumentObject, mixedRepCodeOwners);

    expect(axios.post).toHaveBeenCalledTimes(2);
    expect(result).toEqual([
      { docSpaceId: 'space1', status: 'already_shared' },
      { docSpaceId: 'space3', data: 'Success for space3', status: 'success' },
      { docSpaceId: 'space4', error: expect.any(Error), status: 'error' }
    ]);
  });
});

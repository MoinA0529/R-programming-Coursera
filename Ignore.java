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
    attestation: true
  };

  const mockRepCodeOwnersSelected = {
    data: [
      { docSpaceId: 'space1', selected: true },
      { docSpaceId: 'space2', selected: false },
      { docSpaceId: 'space3', selected: true }
    ]
  };

  beforeEach(() => {
    axios.post.mockClear();
  });

  it('should make POST requests for each selected docSpaceId', async () => {
    axios.post.mockResolvedValue({ data: 'Success' });

    await reshareService(mockDocumentObject, mockRepCodeOwnersSelected);

    expect(axios.post).toHaveBeenCalledTimes(2);
    expect(axios.post).toHaveBeenNthCalledWith(1, SAVE_FILE_URL, 
      { ...mockDocumentObject, docSpaceId: 'space1' },
      { headers: { 'Content-Type': 'application/json' } }
    );
    expect(axios.post).toHaveBeenNthCalledWith(2, SAVE_FILE_URL, 
      { ...mockDocumentObject, docSpaceId: 'space3' },
      { headers: { 'Content-Type': 'application/json' } }
    );
  });

  it('should not make any requests if no docSpaceIds are selected', async () => {
    const noSelectedRepCodeOwners = {
      data: [
        { docSpaceId: 'space1', selected: false },
        { docSpaceId: 'space2', selected: false }
      ]
    };

    await reshareService(mockDocumentObject, noSelectedRepCodeOwners);

    expect(axios.post).not.toHaveBeenCalled();
  });

  it('should throw an error if the API request fails', async () => {
    const mockError = new Error('Network Error');
    axios.post.mockRejectedValue(mockError);

    await expect(reshareService(mockDocumentObject, mockRepCodeOwnersSelected)).rejects.toThrow('Network Error');
    expect(axios.post).toHaveBeenCalledTimes(1);
  });
});

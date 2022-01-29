import junit.framework.TestCase;

import java.security.NoSuchAlgorithmException;

public class CrackerTest extends TestCase {
    // xyz 66b27417d37e024c46526c2f6d358a754fc552f3
    // a! 34800e15707fae815d7c90d49de44aca97e2d759

    public void testGenerate() throws NoSuchAlgorithmException {
        Cracker cracker = new Cracker();
        assertEquals("34800e15707fae815d7c90d49de44aca97e2d759",cracker.generate("a!"));
        assertEquals("66b27417d37e024c46526c2f6d358a754fc552f3",cracker.generate("xyz"));
    }

    public void testCrackSingle() throws NoSuchAlgorithmException, InterruptedException {
        Cracker cracker = new Cracker();
        assertEquals("a!",cracker.crack("34800e15707fae815d7c90d49de44aca97e2d759",3,1));
        assertEquals("xyz",cracker.crack("66b27417d37e024c46526c2f6d358a754fc552f3",3,1));
        assertNull(cracker.crack("66b27417d37e024c46526c2f6d358a754fc552f3", 2, 1));
    }
    public void testCrackMultiple() throws NoSuchAlgorithmException, InterruptedException {
        Cracker cracker = new Cracker();
        assertEquals("a!",cracker.crack("34800e15707fae815d7c90d49de44aca97e2d759",2,2));
        assertEquals("xyz",cracker.crack("66b27417d37e024c46526c2f6d358a754fc552f3",3,2));
        assertNull(cracker.crack("66b27417d37e024c46526c2f6d358a754fc552f3", 2, 1));
        assertEquals("a!",cracker.crack("34800e15707fae815d7c90d49de44aca97e2d759",2,8));
        assertEquals("xyz",cracker.crack("66b27417d37e024c46526c2f6d358a754fc552f3",3,8));
    }
}

using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using NUnit.Framework;

namespace Algo
{
    [TestFixture]
    public class Sort_3
    {
        [Test]
        public void Test1()
        {
          
        }

        public int solution(int[] citations)
        {
            Array.Sort(citations, (x, y) => (x > y) ? -1 : 1);
            for (int i = 0; i < citations.Length; ++i)
            {
                if (i >= citations[i]) return i;
            }

            return citations.Length;
        }
    }
}